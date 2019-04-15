package com.ky.noobcoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Block {
	public String hash;
	public String previousHash;
	private String merkleRoot;
	public List<Transaction> transactions = new ArrayList<Transaction>(); 
	private long timeStamp;
	private int nounce;
	
	public Block(String previousHash) {
		this.previousHash = previousHash;
		this.timeStamp = System.currentTimeMillis();
		this.hash = calculateHash();
	}
	
	public String calculateHash() {
		return StringUtil.applySha256(previousHash+Long.toString(timeStamp)+Integer.toString(nounce)+merkleRoot);
	}
	
	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		long currentTime = System.currentTimeMillis();
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring(0, difficulty).equals(target)) {
			nounce ++;
			hash = calculateHash();
		}
		System.out.println("Block mined!!! : "+hash+" in "+(System.currentTimeMillis()-currentTime)+"ms.");
	}

	// Add transactions to this block
	public boolean addTransaction(Map<String,TransactionOutput> UTXOs, Transaction transaction) {
		// process transaction and check if valid, unless block is genesis block then ignore.
		if (transaction == null)
			return false;
		if ((previousHash != "0")) {
			if ((transaction.processTransaction(UTXOs) != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
}
