import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { IDataProcessor } from 'app/model/core/data-processor.model';
import { createRequestOption } from 'app/util/request-util';
import { IDataProcessorResultDTO } from 'app/model/core/custom/computation.model';


type EntityResponseType = HttpResponse<IDataProcessor>;
type EntityArrayResponseType = HttpResponse<IDataProcessor[]>;

@Injectable({ providedIn: 'root' })
export class DataProcessorCustomService {
    public resourceUrl = '/core/api/data-processors';

    constructor(private http: HttpClient) {}


    execute(identifier, paramMap?: any): Observable<HttpResponse<IDataProcessorResultDTO>> {
      const options = createRequestOption({'identifier': identifier});

      return this.http.post<IDataProcessorResultDTO>(
        this.resourceUrl + '/execute', paramMap || {}, { params: options, observe: 'response' });
    }

    debug(identifier, dataSubProcessorId, paramMap?: any): Observable<HttpResponse<IDataProcessorResultDTO>> {
      const options = createRequestOption({'identifier': identifier, 'dataSubProcessorId': dataSubProcessorId});
      return this.http.post<IDataProcessorResultDTO>(
        this.resourceUrl + '/debug', paramMap || {}, { params: options, observe: 'response' });
    }
}
