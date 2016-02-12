package com.chenxb.news;

import com.chenxb.dao.RotationImageDao;

public class LoadRotation {
	public static void main(String[] args) throws Exception {
		new RotationImageDao().initRotations();
	}

}
