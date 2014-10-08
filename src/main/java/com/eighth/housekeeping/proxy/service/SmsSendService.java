package com.eighth.housekeeping.proxy.service;

public interface SmsSendService {
	public boolean sendSms(String tel, String authCode);
}
