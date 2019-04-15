package com.ky.blockchain;

public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	private int nounce;
	
	public Block(String data, String previousHash) {
		this.setData(data);
		this.previousHash = previousHash;
		this.timeStamp = System.currentTimeMillis();
		this.hash = calculateHash();
	}
	
	public String calculateHash() {
		return StringUtil.applySha256(previousHash+Long.toString(timeStamp)+Integer.toString(nounce)+data);
	}
	
	public void mineBlock(int difficulty) {
		long currentTime = System.currentTimeMillis();
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring(0, difficulty).equals(target)) {
			nounce ++;
			hash = calculateHash();
		}
		System.out.println("Block mined!!! : "+hash+" in "+(System.currentTimeMillis()-currentTime)+"ms.");
	}

	public void setData(String data) {
		this.data = data;
	}
}
