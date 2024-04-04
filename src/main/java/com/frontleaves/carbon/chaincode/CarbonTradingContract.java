package com.frontleaves.carbon.chaincode;

import com.google.gson.Gson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeStub;

/**
 * 智能合约
 * <hr/>
 * 关于碳交易方面的区块链信息处理操作
 *
 * @since v1.0.0
 * @version v1.0.0
 * @author xiao_lfeng
 */
@Contract(
        name = "CarbonTradingContract",
        info = @Info(
                title = "Carbon Contract",
                description = "Hyperlegendary Fabric Carbon Contract",
                version = "v1.0.0",
                license = @License(name = "MIT")
        )
)
@Default
public class CarbonTradingContract implements ContractInterface {
    Gson gson = new Gson();

    /**
     * 创建碳交易合约
     * <hr/>
     * 创建碳交易合约，包括uuid、名称、碳排放量、碳抵消量,并存储到区块链中,返回创建的合约
     *
     * @param ctx-上下文
     * @param key-键
     * @param uuid-uuid
     * @param owner-所有者
     * @param emission-排放量
     * @param money-金额
     * @return CarbonTrade
     */
    @Transaction
    public CarbonTrade createContract(final Context ctx, final String key, final String uuid, final String owner, final int emission, final int money) {
        ChaincodeStub stub = ctx.getStub();
        String carbonState = stub.getStringState(key);

        // 判断 Key 是否存在
        if (!carbonState.isEmpty()) {
            System.out.println("CarbonTrade " + key + " 已存在");
            throw new RuntimeException("CarbonTrade " + key + " 已存在");
        } else {
            // 创建区块链存储区
            CarbonTrade newCarbonTrade = new CarbonTrade(uuid, owner, emission, money);
            String newCarbonTradeJson = gson.toJson(newCarbonTrade);
            stub.putStringState(key, newCarbonTradeJson);
            return newCarbonTrade;
        }
    }

    /**
     * 查询碳交易合约
     * <hr/>
     * 查询碳交易合约，通过键来查询区块链中的碳交易合约
     *
     * @param ctx-上下文
     * @param key-键
     * @return CarbonTrade
     */
    @Transaction
    public CarbonTrade queryTrade(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String carbonState = stub.getStringState(key);

        // 判断内容是否为空
        if (carbonState.isEmpty()) {
            System.out.println("CarbonTrade " + key + " 不存在");
            throw new RuntimeException("CarbonTrade " + key + " 不存在");
        } else {
            return gson.fromJson(carbonState, CarbonTrade.class);
        }
    }

    /**
     * 更改碳交易合约的所有者
     * <hr/>
     * 更改碳交易合约的所有者，通过键来查询区块链中的碳交易合约，并更改所有者
     *
     * @param ctx 上下文
     * @param key 键
     * @param newOwner 新的所有者
     * @return CarbonTrade
     */
    @Transaction
    public CarbonTrade changeOwner(final Context ctx, final String key, final String newOwner) {
        ChaincodeStub stub = ctx.getStub();
        String carbonState = stub.getStringState(key);

        // 判断内容是否为空
        if (carbonState.isEmpty()) {
            System.out.println("CarbonTrade " + key + " 不存在");
            throw new RuntimeException("CarbonTrade " + key + " 不存在");
        } else {
            CarbonTrade carbonTrade = gson.fromJson(carbonState, CarbonTrade.class);
            carbonTrade.setOwner(newOwner);
            String newCarbonTradeJson = gson.toJson(carbonTrade);
            stub.putStringState(key, newCarbonTradeJson);
            return carbonTrade;
        }
    }
}
