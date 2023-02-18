<template>
  <div class="container">
    <a-form layout="vertical" :model="formData">
      <a-space direction="vertical" :size="16">
        <a-card class="general-card">
          <template #title>
            添加单位
          </template>
          <a-row :gutter="80">

            <a-col :span="8">
              <a-form-item
                  label="单位名称"
                  field="name"
              >
                <a-input
                    v-model="formData.name"
                    placeholder="请输入单位名称"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="父id"
                  field="pid"
              >
                <a-input
                    v-model="formData.pid"
                    placeholder="请输入父id"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="图标"
                  field="logo"
              >
                <a-input
                    v-model="formData.logo"
                    placeholder="请输入图标"
                >
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item
                  label="地址"
                  field="address"
              >
                <a-input
                    v-model="formData.address"
                    placeholder="请输入地址"
                >
                </a-input>
              </a-form-item>
            </a-col>

          </a-row>

        </a-card>
      </a-space>
      <div class="actions">
        <a-space>
          <a-button>
            重置
          </a-button>
          <a-button type="primary" :loading="loading" @click="onSubmitClick">
            提交
          </a-button>
        </a-space>
      </div>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import useLoading from '@/hooks/loading';
import {
    CompanyEntityCreate,
    CompanyEntityUpdate,
    CompanyEntityById,
    CompanyEntityPage,
    CompanyEntityDelete,
    CompanyEntityDeletes,
    CompanyEntityQueryRequest,
    CompanyEntityCreateRequest
    } from "./companyApi";

const create:CompanyEntityCreateRequest={
  name: '',
  pid: '',
  logo: '',
  address: '',
}
const formData = ref(create);

const { loading, setLoading } = useLoading();
const onSubmitClick = async () => {
  console.log(formData.value)
  CompanyEntityCreate(formData.value).then(
      (res)=>{
        if (res.code == 20000) {
            Message.success("创建成功")
        }

      }
  )

};
</script>

<script lang="ts">
export default {
  name: 'Add',
};
</script>

<style scoped lang="less">
.container {
  padding: 0 20px 40px 20px;
  overflow: hidden;
}

.actions {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  height: 60px;
  padding: 14px 20px 14px 0;
  background: var(--color-bg-2);
  text-align: right;
}
</style>
