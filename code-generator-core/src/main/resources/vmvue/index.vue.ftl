<template>
  <div class="container">
    <a-card class="general-card" title="查询表格">
      <a-row>
        <a-col :flex="1">
          <a-form
            :model="queryRequest"
            :label-col-props="{ span: 6 }"
            :wrapper-col-props="{ span: 18 }"
            label-align="left"
          >
            <a-row :gutter="16">
<#list fields as field>
  <#if field.query>
    <#if field.fieldType == "LocalDateTime">
                <a-col :span="8">
                  <a-form-item
                    field="${field.fieldName}s"
                    label="${field.fieldDesc}"
                  >
                    <a-date-picker
                      show-time
                      v-model="queryRequest.${field.fieldName}s"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
      <#elseif field.fieldType == "DateTime">
                <a-col :span="8">
                  <a-form-item
                    field="${field.fieldName}s"
                    label="${field.fieldDesc}"
                  >
                    <a-range-picker
                      v-model="queryRequest.${field.fieldName}s"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
      <#else >
              <a-col :span="8">
                <a-form-item
                  field="${field.fieldName}"
                  label="${field.fieldDesc}"
              >
                  <a-input
                    v-model="queryRequest.${field.fieldName}"
                    placeholder="请输入${field.fieldDesc}"
                  />
                </a-form-item>
              </a-col>
    </#if>
  </#if>
</#list>
            </a-row>
          </a-form>
        </a-col>
        <a-divider style="height: 84px" direction="vertical" />
        <a-col :flex="'86px'" style="text-align: right">
          <a-space direction="vertical" :size="18">
            <a-button type="primary" @click="search">
              <template #icon>
                <icon-search />
              </template>
              查询
            </a-button>
            <a-button @click="reset">
              <template #icon>
                <icon-refresh />
              </template>
              重置
            </a-button>
          </a-space>
        </a-col>
      </a-row>
      <a-divider style="margin-top: 0" />
      <a-row style="margin-bottom: 16px">
        <a-col :span="12">
          <a-space>
            <a-button type="primary">
              <template #icon>
                <icon-plus />
              </template>
                新增
            </a-button>

          </a-space>
        </a-col>
        <a-col
          :span="12"
          style="display: flex; align-items: center; justify-content: end"
        >
        </a-col>
      </a-row>
      <a-table
        row-key="id"
        :loading="loading"
        :pagination="pagination"
        :columns="columns"
        :data="response"
        :bordered="false"
        :size="size"
        @page-change="onPageChange"
      >
        <template #index="{ rowIndex }">
          {{ rowIndex + 1 + (pagination.current - 1) * pagination.pageSize }}
        </template>
        <template #operations="{ record }">
          <a-button size="small" type="text" @click="show(record)">
            查看
          </a-button>
          <a-button size="small" type="text" @click="update(record)">
            修改
          </a-button>
          <a-button size="small" type="text" @click="delte(record)">
            删除
          </a-button>
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import {computed, ref, reactive, onMounted} from 'vue';
  import useLoading from '@/hooks/loading';
  import { Pagination } from '@/types/global';
  import type { TableColumnData } from '@arco-design/web-vue/es/table/interface';
  import {
    ${entityName}Create,
    ${entityName}Update,
    ${entityName}ById,
    ${entityName}Page,
    ${entityName}Delete,
    ${entityName}Deletes,
    ${entityName}QueryRequest
    } from "./${tableName}Api";

  type SizeProps = 'mini' | 'small' | 'medium' | 'large';

  // 外部
  const generateFormModel:${entityName}QueryRequest = {
    <#list  fields as field>
      // ${field.fieldDesc}
      ${field.fieldName}:"",
    <#if field.range >
      ${field.fieldName}s:[],
    <#elseif field.fieldType == "LocalDateTime" && field.range!=true>
      ${field.fieldName}s:[],
    <#elseif field.fieldType == "DateTime" && field.range!=true>
      ${field.fieldName}s:[],
    </#if>
    </#list>
  };
  const { loading, setLoading } = useLoading(true);
  const response = ref([]);
  const queryRequest = ref(generateFormModel);

  const size = ref<SizeProps>('medium');

  const basePagination: Pagination = {
    current: 1,
    pageSize: 20,
  };
  const pagination = reactive({
    ...basePagination,
  });

  // 需要显示的字段
  const columns = computed<TableColumnData[]>(() => [
<#list  fields as field>
   <#if field.show>
    {
      title: "${field.fieldDesc}",
      dataIndex: '${field.fieldName}',
    },
    </#if>
  </#list>
  ]);

  // 搜索接口
  const fetchData =  () => {
    setLoading(true);
    try {
      let page = {
        size: basePagination.pageSize,
        page: basePagination.current
      }
      let c = ${entityName}Page(queryRequest.value,page)
      response.value = [];
      pagination.current = 0;
      pagination.total = 100;
    } catch (err) {
    } finally {
      setLoading(false);
    }
  };


  // 查看所需调用接口
  const show = (recode: any) => {
    console.log(recode);
  }
  // 更新所需调用接口
  const update = (recode: any) => {
    console.log(recode);
  }
  // 删除所修调用接口
  const delte = (recode: any) => {
    console.log(recode);
  }
  // 搜索接口
  const search = () => {
    console.log(queryRequest.value);
  };
  // 当页码发送变化时处理的接口
  const onPageChange = (current: number) => {
  };

   // 查询条件清空的情况
  const reset = () => {
  };


  onMounted(()=>{
    fetchData()
  })


</script>

<script lang="ts">
  export default {
    name: '${entityName}',
  };
</script>

<style scoped lang="less">
  .container {
    padding: 0 20px 20px 20px;
  }
  :deep(.arco-table-th) {
    &:last-child {
      .arco-table-th-item-title {
        margin-left: 16px;
      }
    }
  }
  .action-icon {
    margin-left: 12px;
    cursor: pointer;
  }
  .active {
    color: #0960bd;
    background-color: #e3f4fc;
  }
  .setting {
    display: flex;
    align-items: center;
    width: 200px;
    .title {
      margin-left: 12px;
      cursor: pointer;
    }
  }
</style>
