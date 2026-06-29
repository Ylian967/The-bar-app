<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import SidebarBarman from '../../components/barman/SidebarBarman.vue'
import OngletCocktails from '../../components/barman/OngletCocktails.vue'
import OngletCategories from '../../components/barman/OngletCategories.vue'
import OngletIngredients from '../../components/barman/OngletIngredients.vue'

const router = useRouter()
const auth = useAuthStore()
const onglet = ref<'cocktails' | 'categories' | 'ingredients'>('cocktails')

function deconnexion() {
  auth.logout()
  router.push({ name: 'login' })
}
</script>

<template>
  <div class="layout">
    <SidebarBarman />
    <main class="main">
      <header class="tbar">
        <div>
          <h2>Gestion de la carte</h2>
          <p class="sub">Catégories · Cocktails · Ingrédients</p>
        </div>
        <div class="who">
          <span class="avt">{{ (auth.nom ?? 'B')[0] }}</span>
          Barmaker · {{ auth.nom }}
          <button class="out" @click="deconnexion">Déconnexion</button>
        </div>
      </header>

      <div class="tabs">
        <button class="t" :class="{ on: onglet === 'categories' }" @click="onglet = 'categories'">Catégories</button>
        <button class="t" :class="{ on: onglet === 'cocktails' }" @click="onglet = 'cocktails'">Cocktails</button>
        <button class="t" :class="{ on: onglet === 'ingredients' }" @click="onglet = 'ingredients'">Ingrédients</button>
      </div>

      <OngletCocktails v-if="onglet === 'cocktails'" />
      <OngletCategories v-else-if="onglet === 'categories'" />
      <OngletIngredients v-else />
    </main>
  </div>
</template>

<style scoped>
.layout { display: flex; min-height: 100vh; background: var(--cream); }
.main { flex: 1; padding: 26px 30px; }
.tbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.tbar h2 { font-size: 24px; font-weight: 800; }
.sub { color: var(--ink-soft); font-size: 13px; }
.who { display: flex; align-items: center; gap: 10px; font-size: 13px; font-weight: 600; }
.avt { width: 38px; height: 38px; border-radius: 50%; background: linear-gradient(135deg, var(--grape), #3fb6ff); display: grid; place-items: center; color: #fff; font-weight: 700; }
.out { background: #fff; border: 1px solid var(--line); border-radius: 12px; padding: 8px 12px; color: var(--ink-soft); font-size: 12px; font-weight: 600; }
.tabs { display: flex; gap: 8px; background: #f2ecf7; padding: 5px; border-radius: 16px; max-width: 360px; margin-bottom: 22px; }
.t { flex: 1; padding: 10px; border-radius: 12px; font-weight: 600; font-size: 13px; color: var(--ink-soft); background: transparent; }
.t.on { background: #fff; color: var(--ink); box-shadow: var(--shadow-sm); }
</style>
