import axios from 'axios';
import {PageAndSortRequest} from "@/views/generator/commpn";
import qs from 'query-string';


// 添加用户
export function UserEntityCreate(request:UserEntityCreateRequest) {
  return axios.post('/user/create',request);
}

// 修改用户
export function UserEntityUpdate(request:UserEntityUpdateRequest) {
  return axios.post('/user/update',request);
}

// 根据id查询
export function UserEntityById(id:string) {
  return axios.get('/user/byId',{params:{id}});
}

// 根据id集合查询
export function UserEntityByIds(ids:string[]) {
  return axios.get('/user/byIds',{params: {ids: ids.join(",")}});
}


// 用户列表
export function UserEntityList(request:UserEntityQueryRequest) {
  return axios.get('/user/list');
}


// 用户分页
export function UserEntityPage(request:UserEntityQueryRequest,page:PageAndSortRequest) {
  return axios.get('/user/page');
}

// 单个删除用户
export function UserEntityDelete(id:string) {
  return axios.post('/user/delete',);
}
// 多个删除用户
export function UserEntityDeletes(id:string[]) {
  return axios.post('/user/deletes');
}





// 用户响应参数
export interface  UserEntityResponse{
  // 用户名
  username:string;
  // 年龄
  age:string;
  // 密码
  password:string;
  // 邮箱
  email:string;
  // 手机
  phone:string;
  // 生日
  day:string;

  id:  string;
  deleted: string;
  customer: string;
  version: string;
  createTime: string;
  updateTime: string;
  createUserId: string;
  updateUserId: string;

}

// 用户创建参数
export interface UserEntityCreateRequest{
id:  string;
  // 用户名
  username:string;
  // 年龄
  age:string;
  // 密码
  password:string;
  // 邮箱
  email:string;
  // 手机
  phone:string;
  // 生日
  day:string;
}

// 用户修改参数
export interface UserEntityUpdateRequest{
  // 用户名
    username:string;
  // 年龄
    age:string;
  // 密码
    password:string;
  // 邮箱
    email:string;
  // 手机
    phone:string;
  // 生日
    day:string;
}

// 用户查询参数
export interface UserEntityQueryRequest{
  // 用户名
  username:string;

  // 年龄
  age:string;
  ages:string[];

  // 密码
  password:string;

  // 邮箱
  email:string;

  // 手机
  phone:string;

  // 生日
  day:string;

}