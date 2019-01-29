import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';
import { DataProcessorParameterService } from './data-processor-parameter.service';
import { DataProcessorParameterComponent } from './data-processor-parameter.component';
import { DataProcessorParameterDetailComponent } from './data-processor-parameter-detail.component';
import { DataProcessorParameterUpdateComponent } from './data-processor-parameter-update.component';
import { DataProcessorParameterDeletePopupComponent } from './data-processor-parameter-delete-dialog.component';
import { IDataProcessorParameter } from 'app/shared/model/core/data-processor-parameter.model';

@Injectable({ providedIn: 'root' })
export class DataProcessorParameterResolve implements Resolve<IDataProcessorParameter> {
    constructor(private service: DataProcessorParameterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DataProcessorParameter> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DataProcessorParameter>) => response.ok),
                map((dataProcessorParameter: HttpResponse<DataProcessorParameter>) => dataProcessorParameter.body)
            );
        }
        return of(new DataProcessorParameter());
    }
}

export const dataProcessorParameterRoute: Routes = [
    {
        path: 'data-processor-parameter',
        component: DataProcessorParameterComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DataProcessorParameters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-processor-parameter/:id/view',
        component: DataProcessorParameterDetailComponent,
        resolve: {
            dataProcessorParameter: DataProcessorParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessorParameters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-processor-parameter/new',
        component: DataProcessorParameterUpdateComponent,
        resolve: {
            dataProcessorParameter: DataProcessorParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessorParameters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-processor-parameter/:id/edit',
        component: DataProcessorParameterUpdateComponent,
        resolve: {
            dataProcessorParameter: DataProcessorParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessorParameters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataProcessorParameterPopupRoute: Routes = [
    {
        path: 'data-processor-parameter/:id/delete',
        component: DataProcessorParameterDeletePopupComponent,
        resolve: {
            dataProcessorParameter: DataProcessorParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataProcessorParameters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
