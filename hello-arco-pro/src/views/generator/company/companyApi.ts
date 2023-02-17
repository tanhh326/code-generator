import axios from 'axios';
import {PageAndSortRequest} from "@/views/generator/commpn";
import qs from 'query-string';


// 添加单位
export function CompanyEntityCreate(request:CompanyEntityCreateRequest) {
  return axios.post('/company/create',request);
}

// 修改单位
export function CompanyEntityUpdate(request:CompanyEntityUpdateRequest) {
  return axios.post('/company/update',request);
}

// 根据id查询
export function CompanyEntityById(id:string) {
  return axios.get('/company/byId',{params:{id}});
}

// 根据id集合查询
export function CompanyEntityByIds(ids:string[]) {
  return axios.get('/company/byIds',{params: {ids: ids.join(",")}});
}


// 单位列表
export function CompanyEntityList(request:CompanyEntityQueryRequest) {
  return axios.get('/company/list');
}


// 单位分页
export function CompanyEntityPage(request:CompanyEntityQueryRequest,page:PageAndSortRequest) {
  return axios.get('/company/page',{params:Object.assign(request,page)});
}

// 单个删除单位
export function CompanyEntityDelete(id:string) {
  return axios.post('/company/delete',);
}
// 多个删除单位
export function CompanyEntityDeletes(id:string[]) {
  return axios.post('/company/deletes');
}





// 单位响应参数
export interface  CompanyEntityResponse{
  // 单位名称
  name:string;
  // 父id
  pid:string;
  // 图标
  logo:string;
  // 地址
  address:string;

  id:  string;
  deleted: string;
  customer: string;
  version: string;
  createTime: string;
  updateTime: string;
  createUserId: string;
  updateUserId: string;

}

// 单位创建参数
export interface CompanyEntityCreateRequest{
id:  string;
  // 单位名称
  name:string;
  // 父id
  pid:string;
  // 图标
  logo:string;
  // 地址
  address:string;
}

// 单位修改参数
export interface CompanyEntityUpdateRequest{
  // 单位名称
    name:string;
  // 父id
    pid:string;
  // 图标
    logo:string;
  // 地址
    address:string;
}

// 单位查询参数
export interface CompanyEntityQueryRequest{
  // 单位名称
  name:string;

  // 父id
  pid:string;

  // 图标
  logo:string;

  // 地址
  address:string;

}