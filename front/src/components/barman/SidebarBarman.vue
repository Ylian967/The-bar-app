<template>
  <aside class="side">
    <div class="lg">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
        <path d="M4 4h16l-8 9z" /><path d="M12 13v6" /><path d="M8 21h8" />
      </svg>
    </div>
    <nav class="nav">
      <RouterLink to="/admin/commandes" class="i" active-class="on" title="Commandes à traiter">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M6 3h12v18l-2.5-1.6L13 20.4 10.5 19 8 20.4 6 21z" /><path d="M9 8h6M9 12h5" /></svg>
      </RouterLink>
      <RouterLink to="/admin/carte" class="i" active-class="on" title="Gestion de la carte">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4 5h16l-8 8z" /><path d="M12 13v6" /><path d="M8 21h8" /></svg>
      </RouterLink>
    </nav>
  </aside>
</template>

<style scoped>
.side {
  width: 84px;
  flex-shrink: 0;
  background: #241046;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 26px;
  padding: 26px 0;
  color: #fff;
}
.lg { width: 46px; height: 46px; border-radius: 14px; background: var(--accent); display: grid; place-items: center; }
.lg svg { width: 26px; height: 26px; color: #fff; }
.nav { display: flex; flex-direction: column; gap: 16px; }
.i { width: 46px; height: 46px; border-radius: 14px; display: grid; place-items: center; color: #9c8dcb; }
.i svg { width: 21px; height: 21px; }
.i.on { background: rgba(255, 255, 255, 0.12); color: #fff; }
</style>
