package kz.nmbet.betradar.dao.service;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPublicService {

	@Autowired
	private DSLContext create;
}
