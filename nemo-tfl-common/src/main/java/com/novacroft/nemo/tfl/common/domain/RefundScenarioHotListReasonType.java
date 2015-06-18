package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;


@Entity
@Table(name="REFUNDSCENARIOHOTLISTTYPE")
public class RefundScenarioHotListReasonType extends AbstractBaseEntity {
    
    private static final long serialVersionUID = -7343256170848480213L;

    @SequenceGenerator(name = "REFUNDSCENARIOHOTLISTREASONTYPE_SEQ", sequenceName = "REFUNDSCENARIOHOTLISTREASONTYPE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFUNDSCENARIOHOTLISTREASONTYPE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id; 
    }

    protected String refundScenario;
   
    protected HotlistReason hotListReaon;
    
    
    @Transient
    public RefundScenarioEnum getRefundScenarioEnum(){
        return RefundScenarioEnum.find(refundScenario);
    }
    
    
    public void setRefundScenarioEnum(RefundScenarioEnum refundScenarioEnum){
        refundScenario = refundScenarioEnum.code();
    }


    @ManyToOne
    @JoinColumn(name="HOTLISTREASON_ID")
    public HotlistReason getHotListReaon() {
        return hotListReaon;
    }


    public void setHotListReaon(HotlistReason hotListReaon) {
        this.hotListReaon = hotListReaon;
    }

    @Column
    public String getRefundScenario() {
        return refundScenario;
    }


    public void setRefundScenario(String refundScenario) {
        this.refundScenario = refundScenario;
    }
    
    
    
    
}
