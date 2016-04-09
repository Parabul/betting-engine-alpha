package kz.nmbet.betradar.dao.service;

import java.util.List;

import javax.transaction.Transactional;

import kz.nmbet.betradar.dao.domain.entity.GlOutrightEntity;
import kz.nmbet.betradar.dao.repository.GlOutrightEntityRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicOutrightService {

	private static final Logger logger = LoggerFactory
			.getLogger(PublicOutrightService.class);

	@Autowired
	private GlOutrightEntityRepository outrightEntityRepository;

	@Transactional
	public List<GlOutrightEntity> findAll() {
		logger.info("######## PublicOutrightService ########### findAll ################");
		return outrightEntityRepository.findAll();
	}

}
