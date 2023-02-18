import axios from 'axios';
import {PageAndSortRequest} from "@/views/generator/commpn";
import qs from 'query-string';


// 添加部门
export function DeptEntityCreate(request:DeptEntityCreateRequest) {
  return axios.post('/dept/create',request);
}

// 修改部门
export function DeptEntityUpdate(request:DeptEntityUpdateRequest) {
  return axios.post('/dept/update',request);
}

// 根据id查询
export function DeptEntityById(id:string) {
  return axios.get('/dept/byId',{params:{id}});
}

// 根据id集合查询
export function DeptEntityByIds(ids:string[]) {
  return axios.get('/dept/byIds',{params: {ids: ids.join(",")}});
}

// 根据id集合查询（树结构）
export function DeptEntityByIdsTree(ids:string[]){
  return axios.get('/dept/byIds/tree',{params: {ids: ids.join(",")});
}

// 部门列表
export function DeptEntityList(request:DeptEntityQueryRequest) {
  return axios.get('/dept/list');
}

// 部门列表(树结构)
export function DeptEntityListTree(request:DeptEntityQueryRequest){
  return axios.get('/dept/list/tree');
}

// 部门分页
export function DeptEntityPage(request:DeptEntityQueryRequest,page:PageAndSortRequest) {
  return axios.get('/dept/page',{params:Object.assign(request,page)});
}

// 单个删除部门
export function DeptEntityDelete(id:string) {
  return axios.post('/dept/delete',);
}
// 多个删除部门
export function DeptEntityDeletes(id:string[]) {
  return axios.post('/dept/deletes');
}


// 根据单位id查询部门
export function DeptEntityFindByCompanyId(companyId:string){
  return axios.get('/dept/findByCompanyId',);
}

// 根据单位id查询部门(树结构)
export function DeptEntityFindByCompanyIdTree(companyId:string){
  return axios.get('/dept/findByCompanyId/tree');
}
// 根据单位id查询部门集合
export function DeptEntityFindByCompanyIds(companyIds:string[]){
  return axios.get('/dept/findByCompanyIds',{params: {companyIds: companyIds.join(",")}});
}
export function DeptEntityfindByCompanyIdsTree(companyIds:string[]){
  return axios.get('/dept/findByCompanyIds/tree',{params: {companyIds: companyIds.join(",")}});
}




// 部门响应参数
export interface  DeptEntityResponse{
  // 部门名称
  name:string;
  // 单位id
  companyId:string;
  // 父id
  pid:string;
  // 领导人
  leader:string;

  id:  string;
  deleted: string;
  customer: string;
  version: string;
  createTime: string;
  updateTime: string;
  createUserId: string;
  updateUserId: string;

}

// 部门创建参数
export interface DeptEntityCreateRequest{
  // 部门名称
  name:string;
  // 单位id
  companyId:string;
  // 父id
  pid:string;
  // 领导人
  leader:string;
}

// 部门修改参数
export interface DeptEntityUpdateRequest{
  id:  string;
  // 部门名称
    name:string;
  // 单位id
    companyId:string;
  // 父id
    pid:string;
  // 领导人
    leader:string;
}

// 部门查询参数
export interface DeptEntityQueryRequest{
  // 部门名称
  name:string;

  // 单位id
  companyId:string;

  // 父id
  pid:string;

  // 领导人
  leader:string;

}