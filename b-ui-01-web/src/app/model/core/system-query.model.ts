import { Moment } from 'moment';

export interface ISystemQuery {
    id?: number;
    identifier?: string;
    definition?: string;
    roles?: string;
    createTs?: Moment;
    updateTs?: Moment;
}

export class SystemQuery implements ISystemQuery {
    constructor(
        public id?: number,
        public identifier?: string,
        public definition?: string,
        public roles?: string,
        public createTs?: Moment,
        public updateTs?: Moment
    ) {}
}
