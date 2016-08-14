package kz.nmbet.betradar.dao.service;

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
import kz.nmbet.betradar.dao.repository.GlCashboxRepository;
import kz.nmbet.betradar.dao.repository.GlPaymentOrderRepository;

@Service
public class PaymentService {

	@Autowired
	private GlCashboxRepository cashboxRepository;

	@Autowired
	private GlPaymentOrderRepository paymentOrderRepository;

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

		paymentOrder.setOwner(owner);
		paymentOrder.setSecret(getRandomSecret());
		if (owner.getAmount() < amount)
			throw new IllegalArgumentException();
		owner.setAmount(owner.getAmount() - amount);

		return paymentOrderRepository.save(paymentOrder);
	}

	@Transactional
	public List<GlPaymentOrder> findPaymentOrders(GlUser owner) {
		return paymentOrderRepository.findByOwnerOrderByIdDesc(owner);
	}

}
