<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import type { Cocktail } from '../../types/models'
import FormulaireCocktail from './FormulaireCocktail.vue'
import ImportCocktail from './ImportCocktail.vue'

type Mode = 'aucun' | 'choix' | 'creer' | 'editer' | 'import'

const cocktails = ref<Cocktail[]>([])
const mode = ref<Mode>('aucun')
const cocktailEdite = ref<Cocktail | null>(null)

async function charger() {
  cocktails.value = await catalogueService.listerCocktails()
}
function prixMin(c: Cocktail): number {
  return c.tailles.length ? Math.min(...c.tailles.map((t) => t.prix)) : 0
}
function ajouter() {
  cocktailEdite.value = null
  mode.value = 'choix'
}
function editer(c: Cocktail) {
  cocktailEdite.value = c
  mode.value = 'editer'
}
function remplace(maj: Cocktail) {
  const i = cocktails.value.findIndex((x) => x.id === maj.id)
  if (i !== -1) cocktails.value[i] = maj // mise à jour en place : la carte ne bouge pas
}
async function basculerDuJour(c: Cocktail) {
  remplace(await catalogueService.basculerDuJour(c.id))
}
async function basculerFavori(c: Cocktail) {
  remplace(await catalogueService.basculerFavori(c.id))
}
function termine() {
  mode.value = 'aucun'
  charger()
}

onMounted(charger)
</script>

<template>
  <div>
    <div class="entete">
      <span class="compte">{{ cocktails.length }} cocktails au catalogue</span>
      <button class="add" @click="ajouter">+ Ajouter un cocktail</button>
    </div>

    <div class="grid">
      <article v-for="c in cocktails" :key="c.id" class="card" :class="{ masque: !c.realisable }">
        <div class="pic">
          <img :src="imageUrl(c.imageUrl)" :alt="c.nom" />
          <span v-if="c.favori" class="fav-badge">
            <svg viewBox="0 0 24 24" fill="currentColor"><path d="M12 2l3 6 7 .9-5 4.8 1.2 6.9L12 17.8 5.6 20.6 7 13.7 2 8.9 9 8z" /></svg>
          </span>
          <button
            class="switch"
            :class="{ on: c.duJour }"
            :disabled="!c.realisable"
            :title="c.duJour ? 'À la carte' : 'Pas à la carte'"
            @click="basculerDuJour(c)"
          >
            <span class="knob"></span>
          </button>
          <span v-if="!c.realisable" class="manquant">Ingrédient manquant</span>
        </div>
        <div class="body">
          <div class="nom">{{ c.nom }}</div>
          <div class="ac">{{ c.accroche ?? c.categorie }}</div>
          <div class="prix">dès {{ prixMin(c) }} €</div>
        </div>
        <div class="actions">
          <button class="b mod" @click="editer(c)">Modifier</button>
          <button class="b fav" :class="{ on: c.favori }" :title="c.favori ? 'Retirer des favoris' : 'Mettre en avant'" @click="basculerFavori(c)">
            <svg viewBox="0 0 24 24" :fill="c.favori ? 'currentColor' : 'none'" stroke="currentColor" stroke-width="1.8" stroke-linejoin="round"><path d="M12 2l3 6 7 .9-5 4.8 1.2 6.9L12 17.8 5.6 20.6 7 13.7 2 8.9 9 8z" /></svg>
          </button>
        </div>
      </article>
    </div>

    <!-- Modale ajout / édition / import -->
    <div v-if="mode !== 'aucun'" class="overlay" @click.self="mode = 'aucun'">
      <div class="modal">
        <div v-if="mode === 'choix'" class="choix">
          <h3>Ajouter un cocktail</h3>
          <button class="opt" @click="mode = 'creer'">
            <span class="ico"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 20l4-1L18 9l-3-3L5 16z" /><path d="M13.5 6.5l3 3" /></svg></span>
            <span><b>Créer de zéro</b><small>Nouveau cocktail entièrement personnalisé</small></span>
          </button>
          <button class="opt" @click="mode = 'import'">
            <span class="ico"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 4h16l-8 9z" /><path d="M12 13v6" /><path d="M8 21h8" /></svg></span>
            <span><b>Importer du catalogue</b><small>Choisir un vrai cocktail (TheCocktailDB)</small></span>
          </button>
          <button class="btn ghost" @click="mode = 'aucun'">Annuler</button>
        </div>

        <FormulaireCocktail v-else-if="mode === 'creer'" @saved="termine" @cancel="mode = 'aucun'" />
        <FormulaireCocktail v-else-if="mode === 'editer'" :cocktail="cocktailEdite" @saved="termine" @cancel="mode = 'aucun'" />
        <ImportCocktail v-else-if="mode === 'import'" @imported="termine" @cancel="mode = 'aucun'" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.entete { display: flex; justify-content: space-between; align-items: center; margin-bottom: 18px; }
.compte { font-size: 13px; color: var(--ink-soft); font-weight: 600; }
.add { background: linear-gradient(135deg, var(--coral), var(--mango)); color: #fff; font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 13px; padding: 10px 16px; border-radius: 12px; }

.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(190px, 1fr)); gap: 16px; }
.card { background: #fff; border-radius: 20px; overflow: hidden; box-shadow: var(--shadow-sm); display: flex; flex-direction: column; }
.card.masque { opacity: 0.55; }
.pic { position: relative; height: 120px; }
.pic img { width: 100%; height: 100%; object-fit: cover; }
.fav-badge { position: absolute; top: 10px; left: 10px; width: 28px; height: 28px; border-radius: 50%; background: #fff; display: grid; place-items: center; box-shadow: var(--shadow-sm); }
.fav-badge svg { width: 15px; height: 15px; color: var(--mango); }
.switch { position: absolute; top: 10px; right: 10px; width: 46px; height: 26px; border-radius: 999px; background: rgba(36, 16, 70, 0.4); transition: background 0.18s; }
.switch .knob { position: absolute; top: 3px; left: 3px; width: 20px; height: 20px; border-radius: 50%; background: #fff; transition: left 0.18s; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.25); }
.switch.on { background: var(--teal); }
.switch.on .knob { left: 23px; }
.switch:disabled { opacity: 0.45; cursor: not-allowed; }
.manquant { position: absolute; bottom: 8px; left: 8px; background: var(--coral-d); color: #fff; font-size: 11px; font-weight: 600; padding: 4px 9px; border-radius: 999px; }
.body { padding: 12px 14px 6px; flex: 1; }
.body .nom { font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 15px; }
.body .ac { font-size: 12px; color: var(--ink-soft); margin-top: 2px; }
.body .prix { font-family: 'Poppins', sans-serif; font-weight: 700; color: var(--coral); font-size: 14px; margin-top: 6px; }
.actions { display: flex; gap: 6px; padding: 10px 12px 12px; }
.b { border-radius: 11px; font-weight: 700; font-size: 12px; padding: 8px 10px; font-family: 'Poppins', sans-serif; }
.b.mod { flex: 1; background: #eadfff; color: var(--grape); }
.b.fav { width: 38px; display: grid; place-items: center; background: var(--cream); color: #c3b8d2; }
.b.fav.on { color: var(--mango); background: #fff4e0; }
.b.fav svg { width: 17px; height: 17px; }

.overlay { position: fixed; inset: 0; background: rgba(36, 16, 70, 0.45); display: grid; place-items: center; padding: 20px; z-index: 50; }
.modal { width: 100%; max-width: 560px; max-height: 90vh; overflow-y: auto; }
.choix { background: #fff; border-radius: 20px; padding: 22px; }
.choix h3 { font-size: 17px; font-weight: 700; margin-bottom: 16px; }
.opt { display: flex; align-items: center; gap: 14px; width: 100%; text-align: left; background: var(--cream); border: 1.5px solid var(--line); border-radius: 16px; padding: 16px; margin-bottom: 12px; }
.opt .ico svg { width: 26px; height: 26px; color: var(--coral); }
.opt b { display: block; font-family: 'Poppins', sans-serif; font-size: 15px; }
.opt small { color: var(--ink-soft); font-size: 12px; }
</style>
