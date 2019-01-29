export interface IDataProcessorParameter {
    id?: number;
    dataProcessorId?: number;
    json?: any;
}

export class DataProcessorParameter implements IDataProcessorParameter {
    constructor(public id?: number, public dataProcessorId?: number, public json?: any) {}
}
