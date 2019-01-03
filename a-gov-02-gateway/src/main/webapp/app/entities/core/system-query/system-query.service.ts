import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemQuery } from 'app/shared/model/core/system-query.model';

type EntityResponseType = HttpResponse<ISystemQuery>;
type EntityArrayResponseType = HttpResponse<ISystemQuery[]>;

@Injectable({ providedIn: 'root' })
export class SystemQueryService {
    public resourceUrl = SERVER_API_URL + 'core/api/system-queries';

    constructor(private http: HttpClient) {}

    create(systemQuery: ISystemQuery): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemQuery);
        return this.http
            .post<ISystemQuery>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(systemQuery: ISystemQuery): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemQuery);
        return this.http
            .put<ISystemQuery>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISystemQuery>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISystemQuery[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(systemQuery: ISystemQuery): ISystemQuery {
        const copy: ISystemQuery = Object.assign({}, systemQuery, {
            createTs: systemQuery.createTs != null && systemQuery.createTs.isValid() ? systemQuery.createTs.toJSON() : null,
            updateTs: systemQuery.updateTs != null && systemQuery.updateTs.isValid() ? systemQuery.updateTs.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createTs = res.body.createTs != null ? moment(res.body.createTs) : null;
            res.body.updateTs = res.body.updateTs != null ? moment(res.body.updateTs) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((systemQuery: ISystemQuery) => {
                systemQuery.createTs = systemQuery.createTs != null ? moment(systemQuery.createTs) : null;
                systemQuery.updateTs = systemQuery.updateTs != null ? moment(systemQuery.updateTs) : null;
            });
        }
        return res;
    }
}
