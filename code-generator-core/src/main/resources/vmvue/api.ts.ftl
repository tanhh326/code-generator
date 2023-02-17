import axios from 'axios';
import {PageAndSortRequest} from "@/views/generator/commpn";
import qs from 'query-string';


// 添加${tableDesc}
export function ${entityName}Create(request:${entityName}CreateRequest) {
  return axios.post('/${tableName}/create',request);
}

// 修改${tableDesc}
export function ${entityName}Update(request:${entityName}UpdateRequest) {
  return axios.post('/${tableName}/update',request);
}

// 根据id查询
export function ${entityName}ById(id:string) {
  return axios.get('/${tableName}/byId',{params:{id}});
}

// 根据id集合查询
export function ${entityName}ByIds(ids:string[]) {
  return axios.get('/${tableName}/byIds',{params: {ids: ids.join(",")}});
}

<#if pidField??>
// 根据id集合查询（树结构）
export function ${entityName}ByIdsTree(ids:string[]){
  return axios.get('/${tableName}/byIds/tree',{params: {ids: ids.join(",")});
}
</#if>

// ${tableDesc}列表
export function ${entityName}List(request:${entityName}QueryRequest) {
  return axios.get('/${tableName}/list');
}

<#if pidField??>
// ${tableDesc}列表(树结构)
export function ${entityName}ListTree(request:${entityName}QueryRequest){
  return axios.get('/${tableName}/list/tree');
}
</#if>

// ${tableDesc}分页
export function ${entityName}Page(request:${entityName}QueryRequest,page:PageAndSortRequest) {
  return axios.get('/${tableName}/page',{params:Object.assign(request,page)});
}

// 单个删除${tableDesc}
export function ${entityName}Delete(id:string) {
  return axios.post('/${tableName}/delete',);
}
// 多个删除${tableDesc}
export function ${entityName}Deletes(id:string[]) {
  return axios.post('/${tableName}/deletes');
}


<#if hasFk>
<#list  forinKeyList as fk>
// 根据${fk.fieldDesc}查询${tableDesc}
export function ${entityName}FindBy${fk.fkName?cap_first}Id(${fk.fieldName}:string){
  return axios.get('/${tableName}/findBy${fk.fkName?cap_first}Id',);
}

<#if pidField??>
// 根据${fk.fieldDesc}查询${tableDesc}(树结构)
export function ${entityName}FindBy${fk.fkName?cap_first}IdTree(${fk.fieldName}:string){
  return axios.get('/${tableName}/findBy${fk.fkName?cap_first}Id/tree');
}
</#if>
// 根据${fk.fieldDesc}查询${tableDesc}集合
export function ${entityName}FindBy${fk.fkName?cap_first}Ids(${fk.fieldName}s:string[]){
  return axios.get('/${tableName}/findBy${fk.fkName?cap_first}Ids',{params: {${fk.fieldName}s: ${fk.fieldName}s.join(",")}});
}
<#if pidField??>
export function ${entityName}findBy${fk.fkName?cap_first}IdsTree(${fk.fieldName}s:string[]){
  return axios.get('/${tableName}/findBy${fk.fkName?cap_first}Ids/tree',{params: {${fk.fieldName}s: ${fk.fieldName}s.join(",")}});
}
</#if>

</#list>
</#if>



// ${tableDesc}响应参数
export interface  ${entityName}Response{
<#list  fields as field>
  // ${field.fieldDesc}
  ${field.fieldName}:string;
</#list>

  id:  string;
  deleted: string;
  customer: string;
  version: string;
  createTime: string;
  updateTime: string;
  createUserId: string;
  updateUserId: string;

}

// ${tableDesc}创建参数
export interface ${entityName}CreateRequest{
id:  string;
<#list  fields as field>
  // ${field.fieldDesc}
  ${field.fieldName}:string;
</#list>
}

// ${tableDesc}修改参数
export interface ${entityName}UpdateRequest{
<#list  fields as field>
  // ${field.fieldDesc}
    ${field.fieldName}:string;
</#list>
}

// ${tableDesc}查询参数
export interface ${entityName}QueryRequest{
<#list  fields as field>
  // ${field.fieldDesc}
  ${field.fieldName}:string;
<#if field.range >
  ${field.fieldName}s:string[];
</#if>

</#list>
}