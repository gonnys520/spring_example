package org.gonnys.sample;

import lombok.extern.log4j.Log4j;

@Log4j
public class KoreanCook implements Cook {

	public void init() {
		log.info("Korean Cook init....");
	}

	public void destory() {
		log.info("destroy..........");
	}

}
