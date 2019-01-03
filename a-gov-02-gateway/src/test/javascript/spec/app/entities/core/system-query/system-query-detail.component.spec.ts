/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SystemQueryDetailComponent } from 'app/entities/core/system-query/system-query-detail.component';
import { SystemQuery } from 'app/shared/model/core/system-query.model';

describe('Component Tests', () => {
    describe('SystemQuery Management Detail Component', () => {
        let comp: SystemQueryDetailComponent;
        let fixture: ComponentFixture<SystemQueryDetailComponent>;
        const route = ({ data: of({ systemQuery: new SystemQuery(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SystemQueryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SystemQueryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SystemQueryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.systemQuery).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
