package com.cheng.entity;

/**
 * @author zhengxin
 */
public class DefaultFuture {
	private Response rpcResponse;
	private volatile boolean isSucceed = false;
	private final Object object = new Object();
	public Response getResponse(int timeout){
		synchronized (object){
			while (!isSucceed){
				try {
					object.wait(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return rpcResponse;
		}
	}

	public void setResponse(Response response){
		if(isSucceed){
			return;
		}
		synchronized (object) {
			this.rpcResponse = response;
			this.isSucceed = true;
			object.notify();
		}
	}
}
