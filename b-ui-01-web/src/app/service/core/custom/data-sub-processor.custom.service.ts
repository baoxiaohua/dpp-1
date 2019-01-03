import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { IDataSubProcessor } from 'app/model/core/data-sub-processor.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IDataSubProcessor>;
type EntityArrayResponseType = HttpResponse<IDataSubProcessor[]>;

@Injectable({ providedIn: 'root' })
export class DataSubProcessorCustomService {
    public resourceUrl = '/core/api/data-sub-processors';

    constructor(private http: HttpClient) {}

    reorder(dataProcessorId: number, idList: number[]): Observable<EntityArrayResponseType> {
      const options: HttpParams = new HttpParams().set('dataProcessorId', dataProcessorId.toString());
      return this.http.post<IDataSubProcessor[]>(this.resourceUrl + '/reorder', idList, { params: options, observe: 'response' });
    }
}
