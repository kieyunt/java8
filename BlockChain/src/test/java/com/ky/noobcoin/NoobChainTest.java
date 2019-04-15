package com.ky.noobcoin;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ky.noobcoin.Block;

public class NoobChainTest {
	
	public static List<Block> blockChain = new ArrayList<Block>();
	public static Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //list of all unspent transactions. 
	public static int difficulty = 4;
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;
	
	public static void main(String[] args) {
		//Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		//testWalletAB();
		Wallet coinBase = new Wallet();
		
		//create genesis transaction, which sends 100 NoobCoin to walletA:
		genesisTransaction = new Transaction(coinBase.publicKey, walletA.publicKey, 100f, null); //manually sign the genesis transaction	
		genesisTransaction.generateSignature(coinBase.privateKey);
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));  //its important to store our first transaction in the UTXOs list.
		
		System.out.println("Creating and Mining genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(UTXOs, genesisTransaction);
		addBlock(genesis);
		
		//block1 - wallet A send 40 coins to wallet B 
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWallet A's balance is : "+walletA.getBalance(UTXOs));
		System.out.println("Wallet A attempting to send funds (40) to wallet B...");
		block1.addTransaction(UTXOs, walletA.sendFunds(UTXOs, walletB.publicKey, 40f));
		addBlock(block1);
		System.out.println("Wallet A's balance is : "+walletA.getBalance(UTXOs));
		System.out.println("Wallet B's balance is : "+walletB.getBalance(UTXOs));
		
		//block 2 - Wallet A send 1000 coins to wallet B
		Block block2 = new Block(block1.hash);
		System.out.println("\nWallet A attempting to send funds (1000) to wallet B...");
		block2.addTransaction(UTXOs, walletA.sendFunds(UTXOs, walletB.publicKey, 1000f));
		addBlock(block2);
		System.out.println("Wallet A's balance is : "+walletA.getBalance(UTXOs));
		System.out.println("Wallet B's balance is : "+walletB.getBalance(UTXOs));
		
		//block 3 - Wallet B send 20 coins to wallet A
		Block block3 = new Block(block2.hash);
		System.out.println("\nWallet B is attempting to send funds (20) to wallet A...");
		block3.addTransaction(UTXOs, walletB.sendFunds(UTXOs, walletA.publicKey, 20f));
		addBlock(block3);
		System.out.println("Wallet A's balance is : "+walletA.getBalance(UTXOs));
		System.out.println("Wallet B's balance is : "+walletB.getBalance(UTXOs));
		
		isChainValid();
	}
	
	public void testWalletAB(Wallet walletA, Wallet walletB) {
		//Test public and private keys
		System.out.println("Wallet Private and Public Keys: ");
		System.out.println("Wallet A) "+StringUtil.getStringFromKey(walletA.privateKey)+ " >> "+StringUtil.getStringFromKey(walletA.publicKey));
		System.out.println("Wallet B) "+StringUtil.getStringFromKey(walletB.privateKey)+ " >> "+StringUtil.getStringFromKey(walletB.publicKey));
		//Create a test transaction from WalletA to walletB 
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified? "+transaction.verifySignature());
	}
	
	public static boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		Map<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); 
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		
		System.out.println();
		for(int i=1; i<blockChain.size(); i++) {
			currentBlock = blockChain.get(i);
			previousBlock = blockChain.get(i-1);
			//compare registered hash and calculated hash
			System.out.println("Current hashes compare: "+currentBlock.hash+">>"+currentBlock.calculateHash());
			if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current hashes not equal: "+currentBlock.hash+">>"+currentBlock.calculateHash());
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined : "+currentBlock.hash);
				return false;
			}
			
			//loop thru blockchain transactions
			TransactionOutput tempOutput;
			for(Transaction trans : currentBlock.transactions) {
				if(!trans.verifySignature()) {
					System.out.println("Signature on Transaction ("+trans.transactionId+") is invalid");
					return false;
				}
				if(trans.getInputsValue() != trans.getOutputsValue()) {
					System.out.println("Inputs are not equal to outputs on Transaction ("+trans.transactionId+")");
					return false;
				}
				for(TransactionInput input : trans.inputs) {
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					if(tempOutput==null) {
						System.out.println("Referenced input on Transaction ("+trans.transactionId+") is missing.");
						return false;
					}
					if(input.UTXO.value != tempOutput.value) {
						System.out.println("Referenced input Transaction ("+trans.transactionId+") value is invalid.");
						return false;
					}
					tempUTXOs.remove(input.transactionOutputId);
				}
				for(TransactionOutput output : trans.outputs) {
					tempUTXOs.put(output.id, output);
				}
				if(trans.outputs.get(0).recipient != trans.recipient) {
					System.out.println("#Transaction(" + trans.transactionId + ") output reciepient is not who it should be");
					return false;
				}
				if(trans.outputs.get(1).recipient != trans.sender) {
					System.out.println("#Transaction(" + trans.transactionId + ") output 'change' is not sender.");
					return false;
				}
			}
		}
		System.out.println("Blockchain is valid");
		return true;
	}	
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockChain.add(newBlock);
	}
}
