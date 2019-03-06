export interface IDataProcessorResultDTO {
  success?: boolean;
  error?: string;
  results?: any;
}

export class DataProcessorResultDTO implements IDataProcessorResultDTO {
  constructor(
      public success?: boolean,
      public error?: string,
      public results?: any,
  ) {}
}
