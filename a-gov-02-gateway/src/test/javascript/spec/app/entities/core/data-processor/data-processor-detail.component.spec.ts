/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { DataProcessorDetailComponent } from 'app/entities/core/data-processor/data-processor-detail.component';
import { DataProcessor } from 'app/shared/model/core/data-processor.model';

describe('Component Tests', () => {
    describe('DataProcessor Management Detail Component', () => {
        let comp: DataProcessorDetailComponent;
        let fixture: ComponentFixture<DataProcessorDetailComponent>;
        const route = ({ data: of({ dataProcessor: new DataProcessor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [DataProcessorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataProcessorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataProcessorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataProcessor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
