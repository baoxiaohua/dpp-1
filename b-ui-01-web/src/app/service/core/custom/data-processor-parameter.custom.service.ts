import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IDataProcessorParameter } from 'app/model/core/data-processor-parameter.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IDataProcessorParameter>;
type EntityArrayResponseType = HttpResponse<IDataProcessorParameter[]>;

@Injectable({ providedIn: 'root' })
export class DataProcessorParameterCustomService {
    public resourceUrl = '/core/api/data-processor-parameters';

    constructor(private http: HttpClient) {}

    save(dataProcessorParameter: IDataProcessorParameter): Observable<EntityResponseType> {
        return this.http.post<IDataProcessorParameter>(this.resourceUrl + '/save', dataProcessorParameter, { observe: 'response' });
    }

    findByDataProcessorId(dataProcessorId: number): Observable<EntityResponseType> {
      const options = createRequestOption({'dataProcessorId': dataProcessorId});
      return this.http.get<IDataProcessorParameter>(`${this.resourceUrl}/findByDataProcessorId`, { params: options, observe: 'response' });
    }
}
