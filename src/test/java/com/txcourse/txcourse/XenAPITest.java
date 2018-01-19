package com.txcourse.txcourse;

import com.txcourse.DAO.VpsTemplateDAO;
import com.txcourse.DAOImpl.VpsTemplateDAOImpl;
import com.txcourse.service.VpsService;
import com.txcourse.service.XenAPIService;

public class XenAPITest {
	public static void main(String[] args) throws Exception {
		XenAPIService.createUbuntuFromTemplate();
//		VpsTemplateDAO vDao = new VpsTemplateDAOImpl();
//		System.out.println(vDao.findTemplateDetail("f6ae36cd-dccc-4ad5-ab20-bd81d4cedbd5"));
	}
}
