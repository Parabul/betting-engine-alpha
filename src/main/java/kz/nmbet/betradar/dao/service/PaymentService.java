package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlCashbox;
import kz.nmbet.betradar.dao.domain.entity.GlPaymentOrder;
import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.domain.types.PaymentOrderStatus;
import kz.nmbet.betradar.dao.domain.types.PaymentOrderType;
import kz.nmbet.betradar.dao.repository.GlCashboxRepository;
import kz.nmbet.betradar.dao.repository.GlPaymentOrderRepository;
import kz.nmbet.betradar.utils.MessageByLocaleService;
import kz.nmbet.betradar.utils.SmsUtil;

@Service
public class PaymentService {

	@Autowired
	private GlCashboxRepository cashboxRepository;

	@Autowired
	private GlPaymentOrderRepository paymentOrderRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@Autowired
	private SmsUtil smsUtil;

	@Transactional
	public GlCashbox createCashbox(String title, String address, Double maxAmount) {
		GlCashbox cashbox = new GlCashbox();
		cashbox.setTitle(title);
		cashbox.setAddress(address);
		cashbox.setMaxAmount(maxAmount);

		cashbox.setEnabled(true);

		return cashboxRepository.save(cashbox);
	}

	@Transactional
	public GlCashbox deleteCashbox(Integer id) {
		GlCashbox cashbox = cashboxRepository.findOne(id);
		cashbox.setEnabled(false);
		return cashboxRepository.save(cashbox);
	}

	@Transactional
	public List<GlCashbox> findAllCashboxs() {
		return cashboxRepository.findByEnabledTrue();
	}

	@Transactional
	public GlCashbox findCashbox(Integer id) {
		return cashboxRepository.findOne(id);
	}

	private String getRandomSecret() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	@Transactional
	public GlPaymentOrder createPaymentOrder(GlUser owner, Double amount, Integer cashboxId) {
		GlPaymentOrder paymentOrder = new GlPaymentOrder();
		paymentOrder.setAmount(amount);
		paymentOrder.setCashbox(findCashbox(cashboxId));
		paymentOrder.setOrderStatus(PaymentOrderStatus.CREATED);
		paymentOrder.setCreateDate(new Date());
		paymentOrder.setOrderType(PaymentOrderType.OUT);
		paymentOrder.setOwner(owner);
		paymentOrder.setSecret(getRandomSecret());
		if (owner.getAmount() < amount || amount < 0)
			throw new IllegalArgumentException();
		owner.setAmount(owner.getAmount() - amount);
		String template = messageByLocaleService.getMessage("tmpl.payment.order.sms");
		smsUtil.send(owner, MessageFormat.format(template, amount, paymentOrder.getSecret()));
		return paymentOrderRepository.save(paymentOrder);
	}

	@Transactional
	public GlPaymentOrder footBill(String phoneNumber, String secret) {
		GlPaymentOrder paymentOrder = paymentOrderRepository.findByOwnerEmailAndSecret(phoneNumber, secret);
		if(paymentOrder==null)
			throw new IllegalArgumentException("По указанному номеру и секретному слову платеж не найден");
		if(!paymentOrder.getOrderStatus().equals(PaymentOrderStatus.CREATED)){
			throw new IllegalArgumentException("Платеж на сумму "+paymentOrder.getAmount()+" тенге уже выплачен");			
		}
		paymentOrder.setOrderStatus(PaymentOrderStatus.PAYED);
		paymentOrder.setPaymentDate(new Date());
		return paymentOrderRepository.save(paymentOrder);
	}

	@Transactional
	public GlPaymentOrder paymentOrderIncome(String phoneNumber, Double amount) {
		GlUser owner = userService.findByEmail(phoneNumber);
		if (owner == null)
			throw new IllegalArgumentException("По указанному номеру счет не найден");
		GlPaymentOrder paymentOrder = new GlPaymentOrder();
		paymentOrder.setAmount(amount);
		paymentOrder.setOrderStatus(PaymentOrderStatus.RECEIVED);
		paymentOrder.setCreateDate(new Date());
		paymentOrder.setOrderType(PaymentOrderType.IN);
		paymentOrder.setOwner(owner);

		owner.setAmount(owner.getAmount() + amount);
		String template = messageByLocaleService.getMessage("tmpl.payment.order.received.sms");
		smsUtil.send(owner, MessageFormat.format(template, amount));
		return paymentOrderRepository.save(paymentOrder);
	}

	@Transactional
	public List<GlPaymentOrder> findPaymentOrders(GlUser owner) {
		return paymentOrderRepository.findByOwnerOrderByIdDesc(owner);
	}

}
