export interface BasePageInterface {
  initVariable(params, queryParams): void;
  loadPage(): void;
  eventHandler(event: string): void;
}
