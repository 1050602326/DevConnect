(function () {
    document.querySelectorAll('.toggle-pw').forEach(b => b.addEventListener('click', () => {
        const i = document.getElementById(b.dataset.target);
        const show = i.type === 'password';
        i.type = show ? 'text' : 'password';
        b.textContent = show ? '🙈' : '👁';
        b.setAttribute('aria-label', show ? 'Ocultar contraseña' : 'Mostrar contraseña');
    }));

    const form = document.querySelector('form');
    if (form) form.addEventListener('submit', () => {
        const b = form.querySelector('.btn'); b.classList.add('loading'); b.firstChild.nodeValue = 'Procesando';
    });

    const card = document.querySelector('.card'); let ticking = false;
    if (card) card.addEventListener('mousemove', e => {
        if (ticking) return; ticking = true;
        requestAnimationFrame(() => {
            const r = card.getBoundingClientRect();
            card.style.setProperty('--mx', (e.clientX - r.left) + 'px');
            card.style.setProperty('--my', (e.clientY - r.top) + 'px');
            ticking = false;
        });
    });

    const term = document.getElementById('term');
    if (term) {
        const text = term.dataset.lines.split('|').join('\n'); let n = 0;
        const t = setInterval(() => {
            n++;
            term.innerHTML = text.slice(0, n).replace(/\n/g, '<br>') + '<span class="caret"></span>';
            if (n >= text.length) clearInterval(t);
        }, 18);
    }

    const pw = document.getElementById('password'), bars = document.querySelectorAll('.strength span'), lbl = document.querySelector('.strength-label');
    if (pw && bars.length) pw.addEventListener('input', () => {
        const v = pw.value;
        let s = 0; if (v.length >= 6) s++; if (v.length >= 10) s++; if (/[A-Z]/.test(v) && /[a-z]/.test(v)) s++; if (/\d/.test(v) && /[^A-Za-z0-9]/.test(v)) s++;
        const colors = ['#f85149', '#d29922', '#58a6ff', '#3fb950'], names = ['Débil', 'Aceptable', 'Buena', 'Excelente'];
        bars.forEach((b, i) => b.style.background = i < s ? colors[s - 1] : '');
        lbl.textContent = v ? names[Math.max(s - 1, 0)] : '';
    });

    // Cambio animado Profesional / Empresa (cada rol sigue en su propia página)
    const tabs = document.querySelectorAll('a.tab');
    if (tabs.length) {
        const now = document.body.dataset.role;
        let from = null;
        try { from = sessionStorage.getItem('dc-from'); sessionStorage.removeItem('dc-from'); } catch (e) {}
        if (from && from !== now) {
            // llega desde la otra pestaña: arranca en su posición y se desliza a la actual
            document.body.dataset.role = from;
            tabs.forEach(a => a.classList.toggle('active', a.dataset.role === from));
            const c = document.querySelector('.card'); if (c) c.classList.add('fade-in');
            requestAnimationFrame(() => requestAnimationFrame(() => {
                document.body.dataset.role = now;
                tabs.forEach(a => a.classList.toggle('active', a.dataset.role === now));
            }));
        }
        tabs.forEach(a => a.addEventListener('click', e => {
            if (e.metaKey || e.ctrlKey || e.shiftKey) return;
            e.preventDefault();
            if (a.dataset.role === now) return;
            try { sessionStorage.setItem('dc-from', now); } catch (err) {}
            document.body.dataset.role = a.dataset.role;
            tabs.forEach(t => t.classList.toggle('active', t === a));
            document.body.classList.add('leaving');
            setTimeout(() => { location.href = a.href; }, 300);
        }));
    }
})();