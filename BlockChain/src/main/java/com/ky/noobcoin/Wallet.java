package com.ky.noobcoin;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
	public PrivateKey privateKey;
	public PublicKey publicKey;
	public Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //only UTXOs owned by this wallet.
	
	public Wallet() {
		generateKeyPair();
	}
	
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec esSpec = new ECGenParameterSpec("prime192v1");
			keyGen.initialize(esSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	}
	
	public float getBalance(Map<String,TransactionOutput> publicUTXOs) {
		float total = 0;
		for(Map.Entry<String, TransactionOutput> items : publicUTXOs.entrySet()) {
			TransactionOutput UTXO = items.getValue();
			if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
				UTXOs.put(UTXO.id, UTXO); //add it to our list of unspent transactions.
				total += UTXO.value;
			}
		}
		return total;
	}
	
	//Generates and returns a new transaction from this wallet.
	public Transaction sendFunds(Map<String,TransactionOutput> publicUTXOs, PublicKey _recipient, float value) {
		if(getBalance(publicUTXOs) < value) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
		
		List<TransactionInput> inputs = new ArrayList<TransactionInput>();
		float total = 0;
		for(Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.id));
			if(total > value) break;
		}
		
		Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
		newTransaction.generateSignature(privateKey);
		
		for(TransactionInput input : inputs) {
			UTXOs.remove(input.transactionOutputId);
		}
		return newTransaction;
	}
}
