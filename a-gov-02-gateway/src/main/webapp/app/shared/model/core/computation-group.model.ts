import { Moment } from 'moment';

export interface IComputationGroup {
    id?: number;
    identifier?: string;
    name?: string;
    remark?: string;
    createTs?: Moment;
    updateTs?: Moment;
    deleted?: boolean;
}

export class ComputationGroup implements IComputationGroup {
    constructor(
        public id?: number,
        public identifier?: string,
        public name?: string,
        public remark?: string,
        public createTs?: Moment,
        public updateTs?: Moment,
        public deleted?: boolean
    ) {
        this.deleted = this.deleted || false;
    }
}
