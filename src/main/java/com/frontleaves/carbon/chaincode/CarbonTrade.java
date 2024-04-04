package com.frontleaves.carbon.chaincode;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

/**
 * 区块链参与者模型
 * <hr/>
 * 区块链中参与者的模型，包括uuid、名称、碳排放量、碳抵消量
 *
 * @author xiao_lfeng
 */
@Data
@DataType
@AllArgsConstructor
public class CarbonTrade {
    @Property()
    private String uuid;
    @Property()
    private String owner;
    @Property()
    private int carbonEmission;
    @Property()
    private int money;
}
