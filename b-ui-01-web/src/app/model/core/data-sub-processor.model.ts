import { Moment } from 'moment';

export const enum DataProcessorType {
  SQL_INTERIM = 'SQL_INTERIM',
  SQL_DB = 'SQL_DB',
  CODE_GROOVY = 'CODE_GROOVY',
  SQL_KYLIN = 'SQL_KYLIN'
}

export interface IDataSubProcessor {
    id?: number;
    dataProcessorId?: number;
    name?: string;
    sequence?: number;
    dataProcessorType?: DataProcessorType;
    code?: any;
    outputAsTable?: boolean;
    outputAsObject?: boolean;
    outputAsResult?: boolean;
    createTs?: Moment;
    createBy?: string;
    updateTs?: Moment;
    updateBy?: string;
}

export class DataSubProcessor implements IDataSubProcessor {
    constructor(
        public id?: number,
        public dataProcessorId?: number,
        public name?: string,
        public sequence?: number,
        public dataProcessorType?: DataProcessorType,
        public code?: any,
        public outputAsTable?: boolean,
        public outputAsObject?: boolean,
        public outputAsResult?: boolean,
        public createTs?: Moment,
        public createBy?: string,
        public updateTs?: Moment,
        public updateBy?: string
    ) {
        this.outputAsTable = this.outputAsTable || false;
        this.outputAsObject = this.outputAsObject || false;
        this.outputAsResult = this.outputAsResult || false;
    }
}
