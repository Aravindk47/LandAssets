package hello;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import com.org.team7.LandAssets;

@RestController
@RequestMapping("/team7")
public class EthereumController {
	
	@Value("${team7.contractOwner}")
	private String contractOwner;
	
	@Value("${team7.contractAddress}")
	private String contractAddress;
	

	
	
	@RequestMapping(value="/hitEther", method = RequestMethod.GET)
	public String hitEthereum() throws IOException{
		Web3j web3 = Web3j.build(new HttpService());
		Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		return clientVersion;
	}
	
	
	@RequestMapping(value="/registerRegistrar", method = RequestMethod.POST)
	public TransactionReceipt registerRegistrar(@RequestParam("newRegistrar") String newRegistrar) throws Exception{
		Web3j web3 = Web3j.build(new HttpService());
		//String account = web3.ethAccounts().send().getAccounts().get(2);
		//System.out.println("My acccount is " + account);
		LandAssets assets = LandAssets.load(contractAddress, web3, Credentials.create(contractOwner), 
				new BigInteger("0"), new BigInteger("43641")); 
		//System.out.println(new org.web3j.abi.datatypes.Address("0x39fa6a811e2d184a27e0e15ad9d1d5fa43abb251"));
		TransactionReceipt receipt = assets.addRegistrar(newRegistrar, new BigInteger("3")).send();
		return receipt;
	}
	
	
	@RequestMapping(value="/registerAssets", method = RequestMethod.POST)
	public TransactionReceipt registerAssets(@RequestParam("assetId") String assetId,@RequestParam("ownerAddress") String ownerAddress) throws Exception{
		Web3j web3 = Web3j.build(new HttpService());
		//String account = web3.ethAccounts().send().getAccounts().get(2);
		//System.out.println("My acccount is " + account);
		LandAssets assets = LandAssets.load(contractAddress, web3, Credentials.create(contractOwner), 
				new BigInteger("0"), new BigInteger("167749")); 
		TransactionReceipt receipt = assets.addAssets(new BigInteger(assetId),ownerAddress, new BigInteger("3")).send();
		return receipt;
	}
	
	
	@RequestMapping(value="/viewAssets", method = RequestMethod.GET)
	public String viewAssets(@RequestParam("assetId") String assetId) throws Exception{
		Web3j web3 = Web3j.build(new HttpService());
		//String account = web3.ethAccounts().send().getAccounts().get(2);
		//System.out.println("My acccount is " + account);
		LandAssets assets = LandAssets.load(contractAddress, web3, Credentials.create(contractOwner), 
				new BigInteger("0"), new BigInteger("167749")); 
		String ret = assets.viewAssetsBasedOnOwner(new BigInteger(assetId)).send();
		return ret;
	}

}
