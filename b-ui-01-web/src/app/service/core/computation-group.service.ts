import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';
import { IComputationGroup } from 'app/model/core/computation-group.model';
import { createRequestOption } from 'app/util/request-util';


type EntityResponseType = HttpResponse<IComputationGroup>;
type EntityArrayResponseType = HttpResponse<IComputationGroup[]>;

@Injectable({ providedIn: 'root' })
export class ComputationGroupService {
    public resourceUrl = '/core/api/computation-groups';

    constructor(private http: HttpClient) {}

    create(computationGroup: IComputationGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(computationGroup);
        return this.http
            .post<IComputationGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(computationGroup: IComputationGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(computationGroup);
        return this.http
            .put<IComputationGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IComputationGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IComputationGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(computationGroup: IComputationGroup): IComputationGroup {
        const copy: IComputationGroup = Object.assign({}, computationGroup, {
            createTs: computationGroup.createTs != null && computationGroup.createTs.isValid() ? computationGroup.createTs.toJSON() : null,
            updateTs: computationGroup.updateTs != null && computationGroup.updateTs.isValid() ? computationGroup.updateTs.toJSON() : null
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
            res.body.forEach((computationGroup: IComputationGroup) => {
                computationGroup.createTs = computationGroup.createTs != null ? moment(computationGroup.createTs) : null;
                computationGroup.updateTs = computationGroup.updateTs != null ? moment(computationGroup.updateTs) : null;
            });
        }
        return res;
    }
}
