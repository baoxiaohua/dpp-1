import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ISystemQuery } from 'app/model/core/system-query.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<ISystemQuery>;
type EntityArrayResponseType = HttpResponse<ISystemQuery[]>;

@Injectable({ providedIn: 'root' })
export class SystemQueryService {
    public resourceUrl = '/core/api/system-queries';

    constructor(private http: HttpClient) {}

    create(systemQuery: ISystemQuery): Observable<EntityResponseType> {
        return this.http.post<ISystemQuery>(this.resourceUrl, systemQuery, { observe: 'response' });
    }

    update(systemQuery: ISystemQuery): Observable<EntityResponseType> {
        return this.http.put<ISystemQuery>(this.resourceUrl, systemQuery, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISystemQuery>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISystemQuery[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
