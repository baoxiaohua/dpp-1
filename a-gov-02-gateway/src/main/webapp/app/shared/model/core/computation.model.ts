import { Moment } from 'moment';

export const enum ComputationType {
    KYLIN_SQL = 'KYLIN_SQL'
}

export const enum ComputationStatus {
    DRAFT = 'DRAFT',
    ACTIVE = 'ACTIVE',
    ERROR = 'ERROR'
}

export interface IComputation {
    id?: number;
    identifier?: string;
    name?: string;
    type?: ComputationType;
    status?: ComputationStatus;
    remark?: string;
    createTs?: Moment;
    updateTs?: Moment;
    computationGroupId?: number;
    deleted?: boolean;
}

export class Computation implements IComputation {
    constructor(
        public id?: number,
        public identifier?: string,
        public name?: string,
        public type?: ComputationType,
        public status?: ComputationStatus,
        public remark?: string,
        public createTs?: Moment,
        public updateTs?: Moment,
        public computationGroupId?: number,
        public deleted?: boolean
    ) {
        this.deleted = this.deleted || false;
    }
}
