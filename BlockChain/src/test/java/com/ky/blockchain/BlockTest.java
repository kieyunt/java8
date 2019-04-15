package com.ky.blockchain;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;

public class BlockTest {
	
	public static List<Block> blockChain = new ArrayList<Block>();
	public static int difficulty = 6;

	public static void main(String[] args) {
		Block genesisBlock = new Block("Hi i'm the first block","0");
//		genesisBlock.mineBlock(difficulty);
		System.out.println("Hash for block 1 : "+genesisBlock.hash);

		Block secondBlock = new Block("Yo i'm the second block", genesisBlock.hash);
//		secondBlock.mineBlock(difficulty);
		System.out.println("Hash for block 2 : "+secondBlock.hash);
		
		Block thirdBlock = new Block("Hey i'm the third block", secondBlock.hash);
//		thirdBlock.mineBlock(difficulty);
		System.out.println("Hash for block 3 : "+thirdBlock.hash);
		
		blockChain.add(genesisBlock);
		blockChain.add(secondBlock);
		blockChain.add(thirdBlock);
		
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(blockChain));
		
		//secondBlock.setData("Ye i'm the second block");
		System.out.println("Valid ? "+isChainValid());
		
		System.out.println("Mining Start....");
		genesisBlock.mineBlock(difficulty);
		secondBlock.previousHash = genesisBlock.hash; secondBlock.mineBlock(difficulty);  
		thirdBlock.previousHash = secondBlock.hash; thirdBlock.mineBlock(difficulty); 
		System.out.println("Mined >> Valid ? "+isChainValid());
		System.out.println("\nMined Block Chain :"+new GsonBuilder().setPrettyPrinting().create().toJson(blockChain));
	}
	
	public static boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i=1; i<blockChain.size(); i++) {
			currentBlock = blockChain.get(i);
			previousBlock = blockChain.get(i-1);
			//compare registered hash and calculated hash
			System.out.println("Current hashes compare: "+currentBlock.hash+">>"+currentBlock.calculateHash());
			if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current hashes not equal: "+currentBlock.hash+">>"+currentBlock.calculateHash());
				return false;
			}
			//compare prevous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined : "+currentBlock.hash);
				return false;
			}
		}
		return true;
	}

}
