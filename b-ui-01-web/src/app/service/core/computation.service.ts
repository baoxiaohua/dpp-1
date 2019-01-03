import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { IComputation } from 'app/model/core/computation.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IComputation>;
type EntityArrayResponseType = HttpResponse<IComputation[]>;

@Injectable({ providedIn: 'root' })
export class ComputationService {
    public resourceUrl = '/core/api/computations';

    constructor(private http: HttpClient) {}

    create(computation: IComputation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(computation);
        return this.http
            .post<IComputation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(computation: IComputation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(computation);
        return this.http
            .put<IComputation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IComputation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IComputation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(computation: IComputation): IComputation {
        const copy: IComputation = Object.assign({}, computation, {
            createTs: computation.createTs != null && computation.createTs.isValid() ? computation.createTs.toJSON() : null,
            updateTs: computation.updateTs != null && computation.updateTs.isValid() ? computation.updateTs.toJSON() : null
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
            res.body.forEach((computation: IComputation) => {
                computation.createTs = computation.createTs != null ? moment(computation.createTs) : null;
                computation.updateTs = computation.updateTs != null ? moment(computation.updateTs) : null;
            });
        }
        return res;
    }
}
