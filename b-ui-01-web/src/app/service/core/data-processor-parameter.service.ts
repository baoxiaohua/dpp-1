import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IDataProcessorParameter } from 'app/model/core/data-processor-parameter.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IDataProcessorParameter>;
type EntityArrayResponseType = HttpResponse<IDataProcessorParameter[]>;

@Injectable({ providedIn: 'root' })
export class DataProcessorParameterService {
    public resourceUrl = '/core/api/data-processor-parameters';

    constructor(private http: HttpClient) {}

    create(dataProcessorParameter: IDataProcessorParameter): Observable<EntityResponseType> {
        return this.http.post<IDataProcessorParameter>(this.resourceUrl, dataProcessorParameter, { observe: 'response' });
    }

    update(dataProcessorParameter: IDataProcessorParameter): Observable<EntityResponseType> {
        return this.http.put<IDataProcessorParameter>(this.resourceUrl, dataProcessorParameter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDataProcessorParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDataProcessorParameter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
