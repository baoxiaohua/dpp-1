/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { ComputationGroupDetailComponent } from 'app/entities/core/computation-group/computation-group-detail.component';
import { ComputationGroup } from 'app/shared/model/core/computation-group.model';

describe('Component Tests', () => {
    describe('ComputationGroup Management Detail Component', () => {
        let comp: ComputationGroupDetailComponent;
        let fixture: ComponentFixture<ComputationGroupDetailComponent>;
        const route = ({ data: of({ computationGroup: new ComputationGroup(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ComputationGroupDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ComputationGroupDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ComputationGroupDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.computationGroup).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});