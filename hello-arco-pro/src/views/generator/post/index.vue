<template>
  <div class="container">
    <a-card class="general-card" title="岗位列表">
      <a-row>
        <a-col :flex="1">
          <a-form
            :model="queryRequest"
            :label-col-props="{ span: 6 }"
            :wrapper-col-props="{ span: 18 }"
            label-align="left"
          >
            <a-row :gutter="16">
              <a-col :span="8">
                <a-form-item
                  field="name"
                  label="岗位名称"
              >
                  <a-input
                    v-model="queryRequest.name"
                    placeholder="请输入岗位名称"
                  />
                </a-form-item>
              </a-col>
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
            <a-button type="primary" @click="showAdd">
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
    <div id="addEntity">
      <a-modal v-model:visible="showVisible" @ok="submitAdd" @cancel="cancelAdd">
        <template #title>
          创建岗位
        </template>
            <a-form layout="vertical" :model="formData">
      <a-space direction="vertical" :size="16">
        <a-card class="general-card">
          <a-row :gutter="80">
            <a-col :span="8">
              <a-form-item
                  label="岗位名称"
                  field="name"
              >
                <a-input
                    v-model="formData.name"
                    placeholder="请输入岗位名称"
                >
                </a-input>
              </a-form-item>
            </a-col>

          </a-row>
        </a-card>
      </a-space>
    </a-form>
      </a-modal>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {computed, ref, reactive, onMounted} from 'vue';
import {Message} from "@arco-design/web-vue";

  import useLoading from '@/hooks/loading';
  import { Pagination } from '@/types/global';
  import type { TableColumnData } from '@arco-design/web-vue/es/table/interface';
  import {
    PostEntityCreate,
    PostEntityUpdate,
    PostEntityById,
    PostEntityPage,
    PostEntityDelete,
    PostEntityDeletes,
    PostEntityQueryRequest,
    PostEntityCreateRequest
  } from "./postApi";

  type SizeProps = 'mini' | 'small' | 'medium' | 'large';

  // 外部
  const generateFormModel:PostEntityQueryRequest = {
      // 岗位名称
      name:"",
  };
  const { loading, setLoading } = useLoading(true);
  const response = ref([]);
  const queryRequest = ref(generateFormModel);

  const size = ref<SizeProps>('medium');

  const basePagination: Pagination = {
    current: 0,
    pageSize: 20,
  };
  const pagination = reactive({
    ...basePagination,
  });

  // 需要显示的字段
  const columns = computed<TableColumnData[]>(() => [
    {
      title: "岗位名称",
      dataIndex: 'name',
    },
    {
      title: "操作列",
      dataIndex: 'operations',
      slotName: 'operations',
    },
  ]);

  // 搜索接口
  const fetchData = async () => {
    setLoading(true);
    try {
      let page = {
        size: basePagination.pageSize,
        page: basePagination.current == 0 ? 0 : basePagination.current - 1,
      }
      let {data} = await PostEntityPage(queryRequest.value,page)
      response.value = data.data;
      pagination.current = data.page - 1;
      pagination.total = data.total;
    } catch (err) {
    } finally {
      setLoading(false);
    }
  };


  // 查看所需调用接口
  const show = async (recode: any) => {
    const {data} = await PostEntityById(recode.id);
    console.log("查看接口调用",data)
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


  const create:PostEntityCreateRequest={
    name: '',
  }
  const formData = ref(create);
   // 查询条件清空的情况
  const reset = async () => {
    let page = {
      size: 20,
      page: 0,

    }

    queryRequest.value = {
      // 岗位名称
      name:"",
    }

    let {data} = await PostEntityPage(queryRequest.value,page)
    response.value = data.data;
    pagination.current = data.page -1;
    pagination.total = data.total;

  };

  // 新增显示弹框是否出现标记
  const showVisible = ref(false);
  // 显示新增弹框
  const showAdd = ()=>{
    showVisible.value  = true
  }
  // 提交请求
  const  submitAdd = async ()=>{
      console.log(formData.value)
      await PostEntityCreate(formData.value).then(
        (res)=>{
          if (res.code == 20000) {
            Message.success("创建成功")
            fetchData()
          }
      }
  )
    showVisible.value  = false

  }
  // 取消新增显示框
  const cancelAdd = ()=>{
    showVisible.value  = false

  }

  onMounted(()=>{
    fetchData()
  })


</script>

<script lang="ts">
  export default {
    name: 'PostEntity',
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
