export interface SortRequest {

  sortKey: string
  desc: boolean;
}

export interface PageAndSortRequest {
  sort: SortRequest;
  page:number
  size:number
}