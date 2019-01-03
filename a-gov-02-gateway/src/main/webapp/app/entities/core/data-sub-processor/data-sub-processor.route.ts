import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';
import { DataSubProcessorService } from './data-sub-processor.service';
import { DataSubProcessorComponent } from './data-sub-processor.component';
import { DataSubProcessorDetailComponent } from './data-sub-processor-detail.component';
import { DataSubProcessorUpdateComponent } from './data-sub-processor-update.component';
import { DataSubProcessorDeletePopupComponent } from './data-sub-processor-delete-dialog.component';
import { IDataSubProcessor } from 'app/shared/model/core/data-sub-processor.model';

@Injectable({ providedIn: 'root' })
export class DataSubProcessorResolve implements Resolve<IDataSubProcessor> {
    constructor(private service: DataSubProcessorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DataSubProcessor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DataSubProcessor>) => response.ok),
                map((dataSubProcessor: HttpResponse<DataSubProcessor>) => dataSubProcessor.body)
            );
        }
        return of(new DataSubProcessor());
    }
}

export const dataSubProcessorRoute: Routes = [
    {
        path: 'data-sub-processor',
        component: DataSubProcessorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DataSubProcessors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-sub-processor/:id/view',
        component: DataSubProcessorDetailComponent,
        resolve: {
            dataSubProcessor: DataSubProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataSubProcessors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-sub-processor/new',
        component: DataSubProcessorUpdateComponent,
        resolve: {
            dataSubProcessor: DataSubProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataSubProcessors'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'data-sub-processor/:id/edit',
        component: DataSubProcessorUpdateComponent,
        resolve: {
            dataSubProcessor: DataSubProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataSubProcessors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataSubProcessorPopupRoute: Routes = [
    {
        path: 'data-sub-processor/:id/delete',
        component: DataSubProcessorDeletePopupComponent,
        resolve: {
            dataSubProcessor: DataSubProcessorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataSubProcessors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
