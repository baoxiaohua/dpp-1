import { Moment } from 'moment';

export const enum State {
    DRAFT = 'DRAFT',
    ENABLED = 'ENABLED',
    DISABLED = 'DISABLED',
    ERROR = 'ERROR'
}

export interface IDataProcessor {
    id?: number;
    nameSpace?: string;
    identifier?: string;
    name?: string;
    state?: State;
    restApi?: boolean;
    createTs?: Moment;
    createBy?: string;
    updateTs?: Moment;
    updateBy?: string;
    deleted?: boolean;
}

export class DataProcessor implements IDataProcessor {
    constructor(
        public id?: number,
        public nameSpace?: string,
        public identifier?: string,
        public name?: string,
        public state?: State,
        public restApi?: boolean,
        public createTs?: Moment,
        public createBy?: string,
        public updateTs?: Moment,
        public updateBy?: string,
        public deleted?: boolean
    ) {
        this.restApi = this.restApi || false;
        this.deleted = this.deleted || false;
    }
}
