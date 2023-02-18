<template>
  <div class="container">
    <a-card class="general-card" title="用户列表">
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
                  field="username"
                  label="用户名"
              >
                  <a-input
                    v-model="queryRequest.username"
                    placeholder="请输入用户名"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item
                  field="age"
                  label="年龄"
              >
                  <a-input
                    v-model="queryRequest.age"
                    placeholder="请输入年龄"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item
                  field="email"
                  label="邮箱"
              >
                  <a-input
                    v-model="queryRequest.email"
                    placeholder="请输入邮箱"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item
                  field="phone"
                  label="手机"
              >
                  <a-input
                    v-model="queryRequest.phone"
                    placeholder="请输入手机"
                  />
                </a-form-item>
              </a-col>
                <a-col :span="8">
                  <a-form-item
                    field="days"
                    label="生日"
                  >
                    <a-date-picker
                      show-time
                      v-model="queryRequest.days"
                      style="width: 100%"
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
          创建用户
        </template>
            <a-form layout="vertical" :model="formData">
      <a-space direction="vertical" :size="16">
        <a-card class="general-card">
          <a-row :gutter="80">
            <a-col :span="8">
              <a-form-item
                  label="用户名"
                  field="username"
              >
                <a-input
                    v-model="formData.username"
                    placeholder="请输入用户名"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="年龄"
                  field="age"
              >
                <a-input
                    v-model="formData.age"
                    placeholder="请输入年龄"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="密码"
                  field="password"
              >
                <a-input
                    v-model="formData.password"
                    placeholder="请输入密码"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="邮箱"
                  field="email"
              >
                <a-input
                    v-model="formData.email"
                    placeholder="请输入邮箱"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="手机"
                  field="phone"
              >
                <a-input
                    v-model="formData.phone"
                    placeholder="请输入手机"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="生日"
                  field="day"
              >
                <a-input
                    v-model="formData.day"
                    placeholder="请输入生日"
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
    UserEntityCreate,
    UserEntityUpdate,
    UserEntityById,
    UserEntityPage,
    UserEntityDelete,
    UserEntityDeletes,
    UserEntityQueryRequest,
    UserEntityCreateRequest
  } from "./userApi";

  type SizeProps = 'mini' | 'small' | 'medium' | 'large';

  // 外部
  const generateFormModel:UserEntityQueryRequest = {
      // 用户名
      username:"",
      // 年龄
      age:"",
      ages:[],
      // 密码
      password:"",
      // 邮箱
      email:"",
      // 手机
      phone:"",
      // 生日
      day:"",
      days:[],
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
      title: "用户名",
      dataIndex: 'username',
    },
    {
      title: "年龄",
      dataIndex: 'age',
    },
    {
      title: "邮箱",
      dataIndex: 'email',
    },
    {
      title: "手机",
      dataIndex: 'phone',
    },
    {
      title: "生日",
      dataIndex: 'day',
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
      let {data} = await UserEntityPage(queryRequest.value,page)
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
    const {data} = await UserEntityById(recode.id);
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


  const create:UserEntityCreateRequest={
    username: '',
    age: '',
    password: '',
    email: '',
    phone: '',
    day: '',
  }
  const formData = ref(create);
   // 查询条件清空的情况
  const reset = async () => {
    let page = {
      size: 20,
      page: 0,

    }

    queryRequest.value = {
      // 用户名
      username:"",
      // 年龄
      age:"",
      ages:[],
      // 密码
      password:"",
      // 邮箱
      email:"",
      // 手机
      phone:"",
      // 生日
      day:"",
      days:[],
    }

    let {data} = await UserEntityPage(queryRequest.value,page)
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
      await UserEntityCreate(formData.value).then(
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
    name: 'UserEntity',
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
