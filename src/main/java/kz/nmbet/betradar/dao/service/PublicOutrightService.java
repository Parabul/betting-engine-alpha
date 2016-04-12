package kz.nmbet.betradar.dao.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;
import kz.nmbet.betradar.web.beans.OutrightInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicOutrightService {

	private static final Logger logger = LoggerFactory.getLogger(PublicOutrightService.class);

	@Autowired
	private GlOutrightEntityRepository outrightEntityRepository;

	@Transactional
	public List<OutrightInfo> findAll() {
		logger.debug("PublicOutrightService findAll");
		return outrightEntityRepository.findAll().stream().map(elt -> new OutrightInfo(elt))
				.collect(Collectors.toList());

	}
}
