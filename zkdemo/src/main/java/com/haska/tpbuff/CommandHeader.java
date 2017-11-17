package com.haska.tpbuff;

public class CommandHeader {
	// 4bytes
    private long version;
    // 4bytes
    private long messageType;
    // 4bytes
    private long dispatchID;
    // 4bytes
    private long timestamp;
    
	public long getVersion(){
		return version;
	}
    public void setVersion(long v){
    	version = v;
    }

	public long getMessageType() {
		return messageType;
	}

	public void setMessageType(long messageType) {
		this.messageType = messageType;
	}

	public long getDispatchID() {
		return dispatchID;
	}

	public void setDispatchID(long dispatchID) {
		this.dispatchID = dispatchID;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
