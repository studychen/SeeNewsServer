package com.chenxb.news;

import java.util.ArrayList;

import com.chenxb.dao.ColumnDao;
import com.chenxb.util.ColumnType;

public class ReloadJob {
	public static void main(String[] args) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(11);
		a.add(112);
		System.out.println("array: " + a);
		A aaa = new A(a);
		System.out.println("A: " + aaa);
		a.add(33);
		System.out.println("array: " + a);
		System.out.println("A: " + aaa);

	}

}

class A {
	ArrayList<Integer> arr;

	A(ArrayList<Integer> arr) {
		this.arr = arr;
	}

	@Override
	public String toString() {
		return arr + "";
	}

}
