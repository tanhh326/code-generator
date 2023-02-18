import axios from 'axios';
import {PageAndSortRequest} from "@/views/generator/commpn";
import qs from 'query-string';


// 添加岗位
export function PostEntityCreate(request:PostEntityCreateRequest) {
  return axios.post('/post/create',request);
}

// 修改岗位
export function PostEntityUpdate(request:PostEntityUpdateRequest) {
  return axios.post('/post/update',request);
}

// 根据id查询
export function PostEntityById(id:string) {
  return axios.get('/post/byId',{params:{id}});
}

// 根据id集合查询
export function PostEntityByIds(ids:string[]) {
  return axios.get('/post/byIds',{params: {ids: ids.join(",")}});
}


// 岗位列表
export function PostEntityList(request:PostEntityQueryRequest) {
  return axios.get('/post/list');
}


// 岗位分页
export function PostEntityPage(request:PostEntityQueryRequest,page:PageAndSortRequest) {
  return axios.get('/post/page',{params:Object.assign(request,page)});
}

// 单个删除岗位
export function PostEntityDelete(id:string) {
  return axios.post('/post/delete',);
}
// 多个删除岗位
export function PostEntityDeletes(id:string[]) {
  return axios.post('/post/deletes');
}





// 岗位响应参数
export interface  PostEntityResponse{
  // 岗位名称
  name:string;

  id:  string;
  deleted: string;
  customer: string;
  version: string;
  createTime: string;
  updateTime: string;
  createUserId: string;
  updateUserId: string;

}

// 岗位创建参数
export interface PostEntityCreateRequest{
  // 岗位名称
  name:string;
}

// 岗位修改参数
export interface PostEntityUpdateRequest{
  id:  string;
  // 岗位名称
    name:string;
}

// 岗位查询参数
export interface PostEntityQueryRequest{
  // 岗位名称
  name:string;

}