package com.inglo.giggle.document.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DocumentAggregate {

    private StandardLaborContractNested standardLaborContractNested;
    private PartTimeEmploymentPermit partTimeEmploymentPermit;
    private IntegratedApplication integratedApplication;
    private Reject reject;

    @Builder
    public DocumentAggregate(StandardLaborContractNested standardLaborContractNested,
                              PartTimeEmploymentPermit partTimeEmploymentPermit,
                              IntegratedApplication integratedApplication,
                              Reject reject) {
        this.standardLaborContractNested = standardLaborContractNested;
        this.partTimeEmploymentPermit = partTimeEmploymentPermit;
        this.integratedApplication = integratedApplication;
        this.reject = reject;
    }

    @Builder
    public static class StandardLaborContractNested {
        private StandardLaborContract standardLaborContract;
        private List<ContractWorkDayTime> contractWorkDayTimes;

        @Builder
        public StandardLaborContractNested(StandardLaborContract standardLaborContract, List<ContractWorkDayTime> contractWorkDayTimes) {
            this.standardLaborContract = standardLaborContract;
            this.contractWorkDayTimes = contractWorkDayTimes;
        }
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                        get                                                      -------*
     * -------------------------------------------------------------------------------------------------------------- */

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                        add                                                      -------*
     * -------------------------------------------------------------------------------------------------------------- */

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                      update                                                     -------*
     * -------------------------------------------------------------------------------------------------------------- */

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                      validate                                                   -------*
     * -------------------------------------------------------------------------------------------------------------- */
}
